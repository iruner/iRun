package com.example.irun;

import java.util.List;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//��������������Ԥ��
public class WeatherFragment extends Fragment implements 
     AMapLocalWeatherListener {
	
	private TextView mWeatherLocationTextView;// ����Ԥ���ص�
	private TextView mTodayTimeTextView;// ����ʱ��
	private TextView mTomorrowTimeTextView;// ����ʱ��
	private TextView mNextDayTimeTextView;// ����ʱ��

	private TextView mTodayWeatherTextView;// ��������״��
	private TextView mTomorrowWeatherTextView;// ��������״��
	private TextView mNextDayWeatherTextView;// ��������״��

	private LocationManagerProxy mLocationManagerProxy;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weather, container, false);
		
		mWeatherLocationTextView = (TextView) view.findViewById(R.id.future_weather_location_text);

		mTodayTimeTextView = (TextView) view.findViewById(R.id.today_time_text);
		mTodayWeatherTextView = (TextView) view.findViewById(R.id.today_weather_des_text);
		mTomorrowTimeTextView = (TextView) view.findViewById(R.id.tomorrow_time_text);
		mTomorrowWeatherTextView = (TextView) view.findViewById(R.id.tomorrow_weather_des_text);
		mNextDayTimeTextView = (TextView) view.findViewById(R.id.netx_day_time_text);
		mNextDayWeatherTextView = (TextView) view.findViewById(R.id.netx_day_des_text);
		
		mLocationManagerProxy = LocationManagerProxy.getInstance(getActivity());
		//��ȡδ������Ԥ��
		//�����Ҫͬʱ����ʵʱ��δ��������������ȷ����λ��ȡλ�ú�ʹ��,�ֿ����ã��ɺ��Ա��䡣
		mLocationManagerProxy.requestWeatherUpdates(
				LocationManagerProxy.WEATHER_TYPE_FORECAST, this);
		
		return view;
	}

	@Override
	public void onWeatherForecaseSearched(AMapLocalWeatherForecast arg0) {
		// δ������Ԥ���ص�
		if (arg0 != null && arg0.getAMapException().getErrorCode() == 0) {

			List<AMapLocalDayWeatherForecast> forcasts = arg0
					.getWeatherForecast();
			for (int i = 0; i < forcasts.size(); i++) {
				AMapLocalDayWeatherForecast forcast = forcasts.get(i);
				switch (i) {
				case 0:
					mWeatherLocationTextView.setText(forcast.getCity());
					mTodayTimeTextView.setText("���� ( " + forcast.getDate()
							+ " )");
					mTodayWeatherTextView.setText(forcast.getDayWeather()
							+ "    " + forcast.getDayTemp() + "��/"
							+ forcast.getNightTemp() + "��    "
							+ forcast.getDayWindPower()+"��");

					break;
				case 1:
					mTomorrowTimeTextView.setText("���� ( " + forcast.getDate()
							+ " )");
					mTomorrowWeatherTextView.setText(forcast.getDayWeather()
							+ "    " + forcast.getDayTemp() + "��/"
							+ forcast.getNightTemp() + "��    "
							+ forcast.getDayWindPower()+"��");
					break;
				case 2:
					mNextDayTimeTextView.setText("���� ( " + forcast.getDate()
							+ " )");
					mNextDayWeatherTextView.setText(forcast.getDayWeather()
							+ "    " + forcast.getDayTemp() + "��/"
							+ forcast.getNightTemp() + "��    "
							+ forcast.getDayWindPower()+"��");
					break;
				}
			}
		} else 
		{
			// ��ȡ����Ԥ��ʧ��
			Toast.makeText(getActivity(),"��ȡ����Ԥ��ʧ��:"
			+ arg0.getAMapException().getErrorMessage(), Toast.LENGTH_SHORT).show();
		}	
	}

	@Override
	public void onWeatherLiveSearched(AMapLocalWeatherLive arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// ���ٶ�λ
		mLocationManagerProxy.destroy();
	}
}
