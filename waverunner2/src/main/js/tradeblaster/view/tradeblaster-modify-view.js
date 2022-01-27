/**
 * 
 */
import React from 'react';
import {useNavigate, Link} from 'react-router-dom';
import moment from 'moment';

export default function TradeBlasterModifyView({itemState, appPrefs, inputChange, onOption}) {

	const nav = useNavigate();
	const x = window.location.pathname;
	
	let name = "";
	let stock = "";
	let buyAmount = "";
	let sellAmount = "";
	let algorithum = "Algorithum1";
	let status = "No"
	let runStatus = "Not Running";
	
	if (itemState.item != null) {
		if (itemState.item.name != null) {
			name = itemState.item.name;
		}
		if (itemState.item.stock != null) {
			stock = itemState.item.stock;
		}
		if (itemState.item.buyAmount != null) {
			buyAmount = itemState.item.buyAmount;
		}
		if (itemState.item.sellAmount != null) {
			sellAmount = itemState.item.sellAmount;
		}
		if (itemState.item.algorithum != null) {
			algorithum = itemState.item.algorithum;
		}
		if (itemState.item.status != null) {
			status = itemState.item.status;
			if (itemState.item.status == "Yes") {
				runStatus = "Running";
			} else {
				runStatus = "Not Running";
			}
			
		}
	}
	
	
	let saveTxt = "Save";
	
	let optionsStatus = [{"label":"Yes","value":"Yes"},{"label":"No","label":"No"}]
	let selectOptionsStatus = [];
	for (let i = 0; i < optionsStatus.length; i++) {
		let label = "";
		if (optionsStatus[i].label == null && optionsStatus[i].defaultText != null) {
			label = optionsStatus[i].defaultText;
		} else if (optionsStatus[i].label != null) {
			label = optionsStatus[i].label;
		}
		selectOptionsStatus.push(<option key={i} value={optionsStatus[i].value}>{label}</option>);
	}
	
	let optionsAlgorithum = [{"label":"Bollinger bands","value":"Bollinger bands"},{"label":"Algorithum2","label":"Algorithum2"}]
	let selectOptionsAlgorithum = [];
	for (let i = 0; i < optionsAlgorithum.length; i++) {
		let label = "";
		if (optionsAlgorithum[i].label == null && optionsAlgorithum[i].defaultText != null) {
			label = optionsAlgorithum[i].defaultText;
		} else if (optionsAlgorithum[i].label != null) {
			label = optionsAlgorithum[i].label;
		}
		selectOptionsAlgorithum.push(<option key={i} value={optionsAlgorithum[i].value}>{label}</option>);
	}
	
	let description = "Add Item";
	if (itemState.view == "MODIFY") {
		description = "Modify Item";
	}
	
 	return (
    	<div className="container"> 
			<div className="row">
    			<div>{description}</div>
			</div>
			<div className="row">
				<div>
					<label htmlFor="Name">Name</label>
					<input type="Text" id="name" name="name" className="form-control" autoCapitalize="off" onChange={inputChange} value={name}/>
				</div>
				<div>
					<label htmlFor="Stock">Stock</label>
					<input type="Text" id="stock" name="stock" className="form-control" autoCapitalize="off" onChange={inputChange} value={stock}/>
				</div>
				<div>
					<label htmlFor="BuyAmount">Buy Amount</label>
					<input type="Text" id="buyAmount" name="buyAmount" className="form-control" autoCapitalize="off" onChange={inputChange} value={buyAmount}/>
				</div>
				<div>
					<label htmlFor="SellAmount">Sell Amount</label>
					<input type="Text" id="sellAmount" name="sellAmount" className="form-control" autoCapitalize="off" onChange={inputChange} value={sellAmount}/>
				</div>
				<div>
					<label htmlFor="Algorithum">Algorithum</label>
					<select id="algorithum" name="algorithum" value={algorithum} className="form-control" onChange={inputChange}>
						{selectOptionsAlgorithum}
					</select>
				</div>
				<div>
					<label htmlFor="Status">Active</label>
					<select id="status"  name="status" value={status} className="form-control" onChange={inputChange}>
						{selectOptionsStatus}
					</select>
				</div>
			</div>
			<br/>
			<div className="row">
				<div className="col-sm" > 
					<input type="submit" name="SaveButton" id="SaveButton" className="form-control btn-primary" value="Save" onClick={(e) => onOption("SAVE")}/>
				</div>
				<div className="col-sm" > 
					<input type="submit" name="CancelButton" id="CancelButton" className="form-control" value="Cancel" onClick={(e) => onOption("CANCEL")}/>
				</div>
			</div>
		</div>
    );
}

    	