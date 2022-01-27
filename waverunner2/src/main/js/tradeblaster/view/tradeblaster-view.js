/**
 * 
 */
import React from 'react';
import {useNavigate, Link} from 'react-router-dom';

export default function TradeBlasterView({itemState, appPrefs, onOption}) {

	const nav = useNavigate();
	const x = window.location.pathname;
	
	let automatedTradeTableRows = [];
	// fill latest tradestable
	if (itemState != null && itemState.items != null && itemState.items.length > 0) {
		for (let i = 0; i < itemState.items.length; i++) {
			let cells = [];
			let status = "Not Running";
			cells.push(<td key="NAME">{itemState.items[i].name}</td>);
			cells.push(<td key="STOCK">{itemState.items[i].stock}</td>);
			cells.push(<td key="BUYAMOUNT">{itemState.items[i].buyAmount}</td>);
			cells.push(<td key="SELLAMOUNT">{itemState.items[i].sellAmount}</td>);
			cells.push(<td key="ALGORITHUM">{itemState.items[i].algorithum}</td>);
			if (itemState.items[i].runStatus == "Yes") { status = "Running"; }
			cells.push(<td key="STATUS">{status}</td>);
			cells.push(<td key="MODIFY"><i className="fa fa-edit fa-1" title="Modify" onClick={() => onOption("MODIFY",itemState.items[i])}></i> <i className="fa fa-trash fa-1" title="Delete" onClick={() => onOption("DELETE",itemState.items[i])}></i></td>);
			automatedTradeTableRows.push( <tr key={i} >{cells}</tr> );
		}
	} else {
		automatedTradeTableRows.push(<tr key="1"><td id="EMPTY">Empty</td></tr>);
	}
	let automatedTradeTableBody = <tbody>{automatedTradeTableRows}</tbody>;

 	return (
    	<div className="container"> 
			<div className="row" >
	    		<p className="text-center fs-3 fw-bold" > Trade Blaster </p>
			</div>	
			<div className="row" >
				<div className="col-sm-9" > Automated Trades </div>
				<div className="col-sm-3" > 
					<i className="fa fa-plus-square fa-1 float-end" title="Modify" onClick={(e) => onOption("ADD",e)}></i> 
				</div>
				<table className="table table-striped">
					<thead>
		  				<tr>
		    				<th scope="col">Name</th>
							<th scope="col">Stock</th>
		    				<th scope="col">Buy Amount</th>
		    				<th scope="col">Sell Amount</th>
							<th scope="col">Algorithum</th>
							<th scope="col">Status</th>
							<th scope="col"></th>
		  				</tr>
	  				</thead>
					{automatedTradeTableBody}
				</table>
			</div>
		</div>
    );
}

    	