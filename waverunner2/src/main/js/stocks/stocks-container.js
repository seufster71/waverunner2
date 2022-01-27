/**
 * 
 */
'use-strict';
import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import {useSelector, useDispatch} from 'react-redux';
import * as stockActions from './stocks-actions';
import * as ordersActions from '../orders/orders-action';
import StocksView from "../stocks/view/stocks-view";

function StocksContainer() {
	const stocksState = useSelector((state) => state.stocks);
	const amountState = useSelector((state => state.orders));
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();

	function inputStockChange(type,field,value,event) {
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
			dispatch(stockActions.inputStockChange(field,val,type));
		} else if (type === "TEXT") {
			dispatch(stockActions.inputStockChange(field,val,type));
		} else if (type === "SWITCH") {
			dispatch(stockActions.inputStockChange(field,val,type));
		} else if (type === "SELECT") {
			// this.props.actions.selectChange({field,"value":val});
		} else if (type === "SELECTCLICK") {
			// this.props.actions.selectClick({field,value});
		} else if (type === "SELECTUPDATE") {
			// this.props.actions.selectListUpdate({field,"value":val});
		}
	}

	function inputAmountChange(type,field,value,event) {
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
			dispatch(ordersActions.inputAmountChange(field,val,type));
		} else if (type === "TEXT") {
			dispatch(ordersActions.inputAmountChange(field,val,type));
		} else if (type === "SWITCH") {
			dispatch(ordersActions.inputAmountChange(field,val,type));
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
		
		
	function stockRequest() {
		dispatch(stockActions.getStocks(stocksState.test_field));
	}
	function buyRequest(){
		dispatch(ordersActions.placeOrder(stocksState.test_field, amountState.test_field));
	}
	function trailingStopOrder(){
		dispatch(ordersActions.trailingStopOrder(stocksState.test_field, amountState.test_field));
	}
	function defaultBackTest(){
		dispatch(ordersActions.defualtBackTest(stocksState.test_field, amountState.test_field));
	}
	
	
	if (stocksState != null) {
		return (
			<StocksView
			itemState={stocksState}
			amountState={amountState}
			appPrefs={appPrefs}
			inputStockChange={(e) => inputStockChange("TEXT",'test_field','',e)}
			inputAmountChange={(e)=> inputAmountChange("TEXT",'test_field','',e)}
			stockRequest={stockRequest}
			buyRequest={buyRequest}
			trailingStopOrder={trailingStopOrder}
			defaultBackTest={defaultBackTest}
			onClick = {onClick}
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
		amountState:PropTypes.object,
		itemState: PropTypes.object,
		session: PropTypes.object
	};


export default StocksContainer;