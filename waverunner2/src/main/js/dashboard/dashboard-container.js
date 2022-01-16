/**
 * 
 */
'use-strict';
import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useLocation } from "react-router-dom";
import * as dashboardActions from './dashboard-actions';
import DashboardView from "../dashboard/view/dashboard-view";

function DashboardContainer() {
	const dashboardState = useSelector((state) => state.dashboard);
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();
	
	useEffect(() => {
    	dispatch(dashboardActions.getDashboard())
  	}, [])
	
	if (dashboardState != null) {
		return (
			<DashboardView
			itemState={dashboardState}
			appPrefs={appPrefs}
			/>
		);
	} else {
		return (<div> Loading... </div>);
	}
	
}


export default DashboardContainer;