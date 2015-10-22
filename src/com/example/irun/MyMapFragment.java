package com.example.irun;

import android.provider.SyncStateContract;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RadioGroup;


import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;


public class MyMapFragment extends Fragment implements LocationSource,AMapLocationListener,
		RouteSearch.OnRouteSearchListener,RadioGroup.OnCheckedChangeListener {

	private MapView mapView;
	private AMap aMap;
	private LocationManagerProxy mAMapLocationManager;
	private OnLocationChangedListener mListener;
	private LatLonPoint startPoint = null;
	private LatLonPoint endPoint = null;
	private Marker startMk, targetMk;
	private int walkMode = RouteSearch.WalkDefault;
	private RouteSearch routeSearch;
	private WalkRouteResult walkRouteResult;
	private RadioGroup routegroup;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map, container, false);

		mapView = (MapView) view.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		routegroup = (RadioGroup) view.findViewById(R.id.routegroup);
		routegroup.setOnCheckedChangeListener(this);




		init();
		setUpMap();

		return view;
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {

			case R.id.route1:
				startPoint = new LatLonPoint(23.147,113.257);
				endPoint= new LatLonPoint(23.148,113.325);
				break;
			case R.id.route2:
				startPoint = new LatLonPoint(23.115,113.324);
				endPoint= new LatLonPoint(23.101,113.363);
				break;
			case R.id.route3:
				startPoint = new LatLonPoint(23.066,113.398);
				endPoint= new LatLonPoint(23.063,113.337);
				break;
		}
		searchRouteResult(startPoint, endPoint);
		onWalkRouteSearched(walkRouteResult, 0);

	}




	private void init() {
		if (aMap == null) {

			aMap = mapView.getMap();//创建地图

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}


	private void setUpMap() {


		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);
		MyLocationStyle myLocationStyle = new MyLocationStyle();


		//  AMap.moveCamera(CameraUpdateFactory.zoomTo(14));





	}
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (mListener != null && amapLocation != null) {
			if (amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
			}
		}
	}   //定位成功回调函数

	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
			//此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			//注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
			//在定位结束后，在合适的生命周期调用destroy()方法
			//其中如果间隔时间为-1，则定位只定一次
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;


	}

	public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
		routeSearch = new RouteSearch(getActivity());
		routeSearch.setRouteSearchListener(this);
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				startPoint, endPoint);



		RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo,
				//       walkMode
				2);
		if(query != null)
			routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
	}

	@Override
	public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

	}

	//@Override
	public void onWalkRouteSearched(WalkRouteResult result, int rCode) {

		if (rCode == 0) {


			if (result != null && result.getPaths() != null
					&& result.getPaths().size() > 0) {
				walkRouteResult = result;
				WalkPath walkPath = walkRouteResult.getPaths().get(0);
				aMap.clear();// 清理地图上的所有覆盖物
				WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(getActivity(),
						aMap, walkPath, walkRouteResult.getStartPos(),
						walkRouteResult.getTargetPos());
				walkRouteOverlay.removeFromMap();
				walkRouteOverlay.addToMap();
				walkRouteOverlay.zoomToSpan();
			} else {
				//  ToastUtil.show(RouteActivity.this, R.string.no_result);
			}
		} else if (rCode == 27) {
			//  ToastUtil.show(RouteActivity.this, R.string.error_network);
		} else if (rCode == 32) {
			//  ToastUtil.show(RouteActivity.this, R.string.error_key);
		} else {
			//    ToastUtil.show(RouteActivity.this, getString(R.string.error_other) + rCode);
		}
	}


	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
}
