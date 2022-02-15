/**
 *
 */
import React from "react";
import { useNavigate, Link } from "react-router-dom";

export default function TradeBlasterView({ itemState, appPrefs, onOption }) {
  const nav = useNavigate();
  const x = window.location.pathname;

  let automatedTradeTableRows1 = [];
  // fill latest tradestable
  if (
    itemState != null &&
    itemState.items != null &&
    itemState.items.length > 0
  ) {
    for (let i = 0; i < itemState.items.length; i++) {
      let cells = [];
      let status = "Not Running";
      cells.push(<td key="NAME">{itemState.items[i].name}</td>);
      cells.push(<td key="STOCK">{itemState.items[i].stock}</td>);
      cells.push(<td key="BUYAMOUNT">{itemState.items[i].buyAmount}</td>);
      cells.push(<td key="SELLAMOUNT">{itemState.items[i].sellAmount}</td>);
      cells.push(<td key="ALGORITHUM">{itemState.items[i].algorithum}</td>);
      cells.push(<td key="PROFITLIMIT">{itemState.items[i].profitLimit}</td>);
      cells.push(
        <td key="TRAILINGSTOPPERCENT">
          {itemState.items[i].trailingStopPercent}
        </td>
      );
      if (itemState.items[i].runStatus == "Yes") {
        status = "Running";
      }
      cells.push(<td key="STATUS">{status}</td>);
      cells.push(
        <td key="MODIFY">
          <i
            className="fa fa-edit fa-1"
            title="Modify"
            onClick={() => onOption("MODIFY", itemState.items[i])}
          ></i>{" "}
          <i
            className="fa fa-clipboard fa-1"
            title="Backtest"
            onClick={() => onOption("BACKTEST_VIEW", itemState.items[i])}
          ></i>{" "}
          <i
            className="fa fa-trash fa-1"
            title="Delete"
            onClick={() => onOption("DELETE", itemState.items[i])}
          ></i>
        </td>
      );
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

  let automatedTradeTableRows2 = [];
  // fill latest tradestable
  if (
    itemState != null &&
    itemState.backtests != null &&
    itemState.backtests.length > 0
  ) {
    for (let i = 0; i < itemState.backtests.length; i++) {
      let cells = [];
      cells.push(<td key="NAME">{itemState.backtests[i].name}</td>);
      cells.push(<td key="STOCK">{itemState.backtests[i].stock}</td>);
      cells.push(<td key="TYPE">{itemState.backtests[i].type}</td>);
      cells.push(<td key="STARTDATE">{itemState.backtests[i].startDate}</td>);
      cells.push(<td key="ENDDATE">{itemState.backtests[i].endDate}</td>);
      cells.push(<td key="BUYAMOUNT">{itemState.backtests[i].buyAmount}</td>);
      cells.push(<td key="SELLAMOUNT">{itemState.backtests[i].sellAmount}</td>);
      cells.push(<td key="ALGORITHUM">{itemState.backtests[i].algorithum}</td>);
      cells.push(
        <td key="PROFITLIMIT">{itemState.backtests[i].profitLimit}</td>
      );
      cells.push(
        <td key="TRAILINGSTOPPERCENT">
          {itemState.backtests[i].trailingStopPercent}
        </td>
      );
      cells.push(<td key="MONEYSPENT">{itemState.backtests[i].moneySpent}</td>);
      cells.push(<td key="TOTALVALUE">{itemState.backtests[i].totalValue}</td>);
      automatedTradeTableRows2.push(<tr key={i}>{cells}</tr>);
      cells.push(
        <td key="DELETE">
          <i
            className="fa fa-trash fa-1"
            title="Delete"
            onClick={() => onOption("DELETE_BACKTEST", itemState.backtests[i])}
          ></i>
        </td>
      );
    }
  } else {
    automatedTradeTableRows2.push(
      <tr key="1">
        <td id="EMPTY">Empty</td>
      </tr>
    );
  }
  let automatedTradeTableBody2 = <tbody>{automatedTradeTableRows2}</tbody>;

  return (
    <div className="container">
      <div className="row">
        <p className="text-center fs-3 fw-bold"> Trade Blaster </p>
      </div>
      <div className="row">
        <div className="col-sm-9"> Automated Trades </div>
        <div className="col-sm-3">
          <i
            className="fa fa-plus-square fa-1 float-end"
            title="Modify"
            onClick={(e) => onOption("ADD", e)}
          ></i>
        </div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Stock</th>
              <th scope="col">Buy Amount</th>
              <th scope="col">Sell Amount</th>
              <th scope="col">Algorithum</th>
              <th scope="col">Max Profit</th>
              <th scope="col">Trailing Stop Percent</th>
              <th scope="col">Status</th>
              <th scope="col"></th>
            </tr>
          </thead>
          {automatedTradeTableBody1}
        </table>
        <div className="col-sm-9"> Backtests </div>
        <table className="table table-striped">
          <thead>
            <tr>
              <th scope="col">Name</th>
              <th scope="col">Stock</th>
              <th scope="col">Type</th>
              <th scope="col">Start Date</th>
              <th scope="col">End Date</th>
              <th scope="col">Buy Amount</th>
              <th scope="col">Sell Amount</th>
              <th scope="col">Algorithum</th>
              <th scope="col">Max Profit</th>
              <th scope="col">Trailing Stop Percent</th>
              <th scope="col">Money Spent</th>
              <th scope="col">Total Value</th>
            </tr>
          </thead>
          {automatedTradeTableBody2}
        </table>
      </div>
    </div>
  );
}
