/**
 *
 */
import React from "react";
import { useNavigate, Link } from "react-router-dom";
import moment from "moment";

export default function TradeBlasterModifyView({
  itemState,
  appPrefs,
  inputChange,
  onOption,
}) {
  const nav = useNavigate();
  const x = window.location.pathname;

  let startDate = "";
  let endDate = "";
  if (itemState.item != null) {
    if (itemState.item.startDate != null) startDate = itemState.item.startDate;
    if (itemState.item.endDate != null) endDate = itemState.item.endDate;
  }
  return (
    <div className="container">
      <div className="row">
        <div>Backtest</div>
      </div>
      <div className="row">
        <div>
          <label>Start Date</label>
          <input
            type="date"
            min="2021-01-01"
            max="2021-12-31"
            id="startDate"
            name="startDate"
            className="startDate"
            onChange={inputChange}
            value={startDate}
          />
          <div id="End-Date">
            <label>End Date</label>
            <input
              type="date"
              min="2021-01-01"
              max="2021-12-31"
              id="endDate"
              name="endDate"
              className="endDate"
              onChange={inputChange}
              value={endDate}
            />
          </div>
          <button
            id="trade-button"
            onClick={(e) => {
              e.preventDefault();
              var x = document.getElementById("trade-button");
              if (x.innerHTML === "SWING TRADING") {
                x.innerHTML = "DAY TRADING";
                document.getElementById("trade-button").classList.add("active");

                alert("VERY VOLATILE BE CAUTIOUS");
              } else {
                x.innerHTML = "SWING TRADING";
                document.getElementById("End-Date").classList.remove("active");
                document.getElementById("trade-button").classList.remove("active");
              }
            }}
          >
            SWING TRADING
          </button>
        </div>
      </div>
      <br />
      <div className="row">
        <div className="col-sm">
          <input
            type="submit"
            name="BackTestButton"
            id="BackTestButton"
            className="form-control btn-primary"
            value="BackTest"
            //SECURITY RISK FIX IT
            onClick={() => onOption(document.getElementById("trade-button").innerHTML)}
          />
        </div>
        <div className="col-sm">
          <input
            type="submit"
            name="CancelButton"
            id="CancelButton"
            className="form-control"
            value="Cancel"
            onClick={() => onOption("CANCEL")}
          />
        </div>
      </div>
    </div>
  );
}
