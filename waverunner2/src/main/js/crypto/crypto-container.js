/**
 * 
 */
'use-strict';
import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { useSelector, useDispatch } from 'react-redux';
import * as cryptoActions from './crypto-actions';
import CryptoView from "../crypto/view/crypto-view";

function CryptoContainer() {
	const cryptoState = useSelector((state) => state.crypto);
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
    		dispatch(cryptoActions.inputChange(field,val,type));
		} else if (type === "TEXT") {
    		dispatch(cryptoActions.inputChange(field,val,type));
		} else if (type === "SWITCH") {
    		dispatch(cryptoActions.inputChange(field,val,type));
		} else if (type === "SELECT") {
    		// dispatch(cryptoActions.selectChange({field,"value":val}));
		} else if (type === "SELECTCLICK") {
    		// dispatch(cryptoActions.selectClick({field,value}));
		} else if (type === "SELECTUPDATE") {
			//this.props.actions.selectListUpdate({field,"value":val});
		}
	}

	function onClick() {
    	dispatch(cryptoActions.getCrypto(cryptoState.test_field));
	}
		
	if (cryptoState != null) {
		return (
			<CryptoView
			itemState={cryptoState}
			appPrefs={appPrefs}
			inputChange={(e) => inputChange("TEXT",'test_field','',e)}
			onClick={onClick}
			/>
		);
	} else {
		return (<div> Loading... </div>);
	}
}

CryptoContainer.propTypes = {
		appPrefs: PropTypes.object,
		actions: PropTypes.object,
		stocksState: PropTypes.object,
		session: PropTypes.object
	};


	
export default CryptoContainer;