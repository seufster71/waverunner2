/**
 * 
 */
import React from 'react';
import {useNavigate, Link} from 'react-router-dom';
import moment from 'moment';

export default function TradeBlasterModifyView({itemState, appPrefs, inputChange, onOption}) {

	const nav = useNavigate();
	const x = window.location.pathname;
	
	let buyAmount = "";
	let sellAmount = "";
	let algorithum = "";
	let runStatus = "Not running";
	
	if (itemState != null) {
		if (itemState.item != null) {
			buyAmount = itemState.item.BuyAmount;
			sellAmount = itemState.item.SellAmount;
			algorithum = itemState.item.Algorithum;
			
		}
		
		
	}
	let saveTxt = "Save";
	
 	return (
    	<div> 
    		<div> Trade blaster </div>
			<div> Status: {runStatus} </div>
			<div>
				<label htmlFor="BuyAmount">Buy Amount</label>
				<input type="Text" id="BuyAmount" name="BuyAmount" className="form-control" autoCapitalize="off" onChange={inputChange} value={(itemState.item != null && itemState.item.BuyAmount != null)?itemState.item.BuyAmount:buyAmount}/>
			</div>
			<div>
				<label htmlFor="SellAmount">Sell Amount</label>
				<input type="Text" id="SellAmount" name="SellAmount" className="form-control" autoCapitalize="off" onChange={inputChange.bind(event,"TEXT",'SellAmount','')} value={(itemState.item != null && itemState.item.SellAmount != null)?itemState.item.SellAmount:sellAmount}/>
			</div>
			<div>
				<label htmlFor="Algorithum">Algorithum</label>
				<input type="Text" id="Algorithum" name="Algorithum" className="form-control" autoCapitalize="off" onChange={inputChange.bind(event,"TEXT",'Algorithum','')} value={(itemState.item != null && itemState.item.Algorithum != null)?itemState.item.Algorithum:algorithum}/>
			</div>
			<div> 
				<input type="submit" name="TestButton" id="TestButton" className="form-control" value={saveTxt} onClick={onOption.bind(event,"SAVE")}/>
			</div>
		</div>
    );
}

    	