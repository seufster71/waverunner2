/**
 * 
 */
'use-strict';
import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useLocation } from "react-router-dom";
import * as tradeBlasterActions from './tradeblaster-actions';
import * as orderActions from '../orders/orders-action';
import TradeBlasterView from "../tradeblaster/view/tradeblaster-view";
import TradeBlasterModifyView from "../tradeblaster/view/tradeblaster-modify-view";

function TradeBlasterContainer() {
	const tradeBlasterState = useSelector((state) => state.tradeblaster);
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();
	
	useEffect(() => {
    	dispatch(tradeBlasterActions.list())
  	}, [])
	
	function onOption(code,item) {
		
		switch(code) {
			case 'MODIFY': {
				dispatch(tradeBlasterActions.modifyItem(item));
				return true;
			}
			case 'DELETE': {
				dispatch(tradeBlasterActions.deleteItem(item));
				return true;
			}
			case 'ADD': {
				onAdd();
				return true;
			}
			case 'SAVE': {
				onSave();
				return true;
			}
			case 'BACKTEST':{
			dispatch(orderActions.defualtBackTest(tradeBlasterState.item));
			return true;	
			}
			case 'CANCEL': {
				dispatch(tradeBlasterActions.cancelItem());
				return true;
			}
		}
	}
	
	function onSave() {
		if (tradeBlasterState.item != null) {
			dispatch(tradeBlasterActions.saveItem(tradeBlasterState.item));
		}
	}
	
	function onAdd() {
		dispatch(tradeBlasterActions.addItem());
	}
	
	
	function inputChange (event) {
		let val = "";
		
		if (event != null) {
			if (event.target != null) {
				if (event.target.type === "Number") {
					val = parseInt(event.target.value,0);
				} else {
					val = event.target.value;
				}	
			} else {
				val = event;
			}
			let field = event.target.id;
			dispatch(tradeBlasterActions.inputChange(field,val));
		}
	}
	
	
	
	if (tradeBlasterState != null && tradeBlasterState.view != "MODIFY" && tradeBlasterState.view != "ADD") {
		return (
			<TradeBlasterView
			itemState={tradeBlasterState}
			appPrefs={appPrefs}
			onOption={onOption}
			/>
		);
	} else if (tradeBlasterState != null && (tradeBlasterState.view == "ADD" || tradeBlasterState.view == "MODIFY")) {
		return (
			<TradeBlasterModifyView
			itemState={tradeBlasterState}
			appPrefs={appPrefs}
			inputChange={inputChange}
			onOption={onOption}
			/>
		);

	} else {
		return (<div> Loading... </div>);
	}
	
}


export default TradeBlasterContainer;