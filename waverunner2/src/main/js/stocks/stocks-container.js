/**
 * 
 */
'use-strict';
import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import {useSelector, useDispatch} from 'react-redux';
import * as stockActions from './stocks-actions';
import StocksView from "../stocks/view/stocks-view";

function StocksContainer() {
	const stocksState = useSelector((state) => state.stocks);
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();

	function inputChange(type,field,value,event) {
		let val = "";
		if (value == null || value == "") {
				if (event != null) {
					if (event.target != null) {
						val = event.target.value;
					} else {
						val = event;
					}
				} else {
					val = value;
				}
		} else {
			val = value;
		}
		if (type === "DATE") {
			val = event.toISOString();
			dispatch(stockActions.inputChange(field,val,type));
		} else if (type === "TEXT") {
			dispatch(stockActions.inputChange(field,val,type));
		} else if (type === "SWITCH") {
			dispatch(stockActions.inputChange(field,val,type));
		} else if (type === "SELECT") {
			// this.props.actions.selectChange({field,"value":val});
		} else if (type === "SELECTCLICK") {
			// this.props.actions.selectClick({field,value});
		} else if (type === "SELECTUPDATE") {
			// this.props.actions.selectListUpdate({field,"value":val});
		}
	}
	
	function onClick(value,event) {
		if (value == "List") {
			dispatch(stockActions.getStocks(stocksState.test_field));
		} else if ("Buy") {
			dispatch(stockActions.buy(stocksState.test_field));
		}
		
		
	}
	
	
	if (stocksState != null) {
		return (
			<StocksView
			itemState={stocksState}
			appPrefs={appPrefs}
			inputChange={(e) => inputChange("TEXT",'test_field','',e)}
			onClick={onClick}
			/>
		);
	} else {
		return (<div> Loading... </div>);
	}
	
}

StocksContainer.propTypes = {
		appPrefs: PropTypes.object,
		actions: PropTypes.object,
		stocksState: PropTypes.object,
		session: PropTypes.object
	};


export default StocksContainer;