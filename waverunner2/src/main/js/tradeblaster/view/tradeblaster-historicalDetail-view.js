/**
 *
 */
 import React from "react";
 import { useNavigate, Link } from "react-router-dom";
 
 export default function HistoricalDetailView({ itemState, appPrefs, onOption }) {
   const nav = useNavigate();
   const x = window.location.pathname;
 
   let automatedTradeTableRows1 = [];
   // fill latest tradestable
   if (
     itemState != null &&
     itemState.backtest.historicalDetails != null &&
     itemState.backtest.historicalDetails.length > 0
   ) {
     for (let i = 0; i < itemState.backtest.historicalDetails.length; i++) {
       let cells = [];
       cells.push(<td key="BOUGHTATTIME">{itemState.backtest.historicalDetails[i].stringedBoughtAtTime}</td>);
       cells.push(<td key="BOUGHTAT">{itemState.backtest.historicalDetails[i].boughtAt}</td>);
       cells.push(<td key="SOLDATTIME">{itemState.backtest.historicalDetails[i].stringedSoldAtTime}</td>);
       cells.push(<td key="SOLDAT">{itemState.backtest.historicalDetails[i].soldAt}</td>);
       cells.push(<td key="HIGHPRICE">{itemState.backtest.historicalDetails[i].highPrice}</td>);
       automatedTradeTableRows1.push(<tr key={i}>{cells}</tr>);
     }
   } else {
     automatedTradeTableRows1.push(
       <tr key="1">
         <td id="EMPTY">Empty</td>
       </tr>
     );
   }
   let automatedTradeTableBody1 = <tbody>{automatedTradeTableRows1}</tbody>;

   return (
    <div className="container">
      <div className="row">
        <p className="text-center fs-3 fw-bold"> Historical Details </p>
      </div>
      <button onClick = {
        () => onOption("CANCEL")
      }>Back</button>
      <div className="row">
        <div className="col-sm-3">
        </div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">Bought At Time</th>
              <th scope="col">Bought At</th>
              <th scope="col">Sold At Time</th>
              <th scope="col">Sold At</th>
              <th scope="col">High Price</th>
            </tr>
          </thead>
          {automatedTradeTableBody1}
        </table>
      </div>
    </div>
  );
 }