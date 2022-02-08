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
 
   let calendarValue = "";
   if (itemState.item != null) {
     if (itemState.item.calendar != null)
       calendarValue = itemState.item.calendar;
   }
   return (
     <div className="container">
       <div className="row">
         <div>Backtest</div>
       </div>
       <div className="row">
         <div>
           <label>Date</label>
           <input
             type="date"
             min="2021-01-01"
             max="2021-12-31"
             id="calendar"
             name="calendar"
             className="calendar"
             onChange={inputChange}
             value={calendarValue}
           />
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
             onClick={(e) => onOption("BACKTEST")}
           />
         </div>
         <div className="col-sm">
           <input
             type="submit"
             name="CancelButton"
             id="CancelButton"
             className="form-control"
             value="Cancel"
             onClick={(e) => onOption("CANCEL")}
           />
         </div>
       </div>
     </div>
   );
 }
 