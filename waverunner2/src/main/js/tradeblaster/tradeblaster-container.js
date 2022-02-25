/**
 *
 */
"use-strict";
import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation } from "react-router-dom";
import * as tradeBlasterActions from "./tradeblaster-actions";
import TradeBlasterView from "../tradeblaster/view/tradeblaster-view";
import TradeBlasterModifyView from "../tradeblaster/view/tradeblaster-modify-view";
import TradeBlasterBacktestView from "../tradeblaster/view/tradeblaster-backtest-view";
import HistoricalDetailView from "./view/tradeblaster-historicalDetail-view";

function TradeBlasterContainer() {
  const tradeBlasterState = useSelector((state) => state.tradeblaster);
  const appPrefs = useSelector((state) => state.appPrefs);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(tradeBlasterActions.list());
  }, []);

  function onOption(code, item) {
    switch (code) {
      case "MODIFY": {
        dispatch(tradeBlasterActions.modifyItem(item));
        return true;
      }
      case "DELETE": {
        dispatch(tradeBlasterActions.deleteItem(item));
        return true;
      }
      case "DELETE_BACKTEST":{
        dispatch(tradeBlasterActions.deleteBacktest(item));
        return true;
      }
      case "ADD": {
        onAdd();
        return true;
      }
      case "SAVE": {
        onSave();
        return true;
      }
      case "DAY TRADING": {
        dayTradeBacktest();
        return true;
      }
      case "SWING TRADING":{
        swingTradeBacktest();
        return true;
      }
      case "BACKTEST_VIEW": {
        dispatch(tradeBlasterActions.backTest(item));
        return true;
      }
      case "HISTORICAL_DETAIL_VIEW":{
        dispatch(tradeBlasterActions.historicalDetail(item));
        return true
      }
      case "CANCEL": {
        dispatch(tradeBlasterActions.cancelItem());
        return true;
      }
    }
  }

  function dayTradeBacktest(){
	if (tradeBlasterState.item != null) {
		dispatch(tradeBlasterActions.dayTradeBacktest(tradeBlasterState.item));
	  }
  }

  function swingTradeBacktest(){
    if (tradeBlasterState.item != null) {
      dispatch(tradeBlasterActions.swingTradeBacktest(tradeBlasterState.item));
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

  function inputChange(event) {
    let val = "";

    if (event != null) {
      if (event.target != null) {
        if (event.target.type === "Number") {
          val = parseInt(event.target.value, 0);
        } else {
          val = event.target.value;
        }
      } else {
        val = event;
      }
      let field = event.target.id;
      if(event.target.id === "operand-button")
      field = "operand";
      dispatch(tradeBlasterActions.inputChange(field, val));
    }
  }

  
  if (
    tradeBlasterState != null &&
    tradeBlasterState.view != "MODIFY" &&
    tradeBlasterState.view != "ADD" &&
    tradeBlasterState.view != "BACKTEST" &&
    tradeBlasterState.view != "HISTORICALDETAIL"
  ) {
    return (
      <TradeBlasterView
        itemState={tradeBlasterState}
        appPrefs={appPrefs}
        onOption={onOption}
      />
    );
  } else if (
    tradeBlasterState != null &&
    (tradeBlasterState.view == "ADD" || tradeBlasterState.view == "MODIFY")
  ) {
    return (
      <TradeBlasterModifyView
        itemState={tradeBlasterState}
        appPrefs={appPrefs}
        inputChange={inputChange}
        onOption={onOption}
      />
    );
  } else if (
    tradeBlasterState != null &&
    tradeBlasterState.view == "BACKTEST"
  ) {
    return (
      <TradeBlasterBacktestView
        itemState={tradeBlasterState}
        appPrefs={appPrefs}
        inputChange={inputChange}
        onOption={onOption}
      />
    );
  } else if(
    tradeBlasterState !=null &&
    tradeBlasterState.view == "HISTORICALDETAIL"
  ){
    return (
      <HistoricalDetailView
        itemState={tradeBlasterState}
        appPrefs={appPrefs}
        onOption={onOption}
      />
    );
  }
  else {
    return <div> Loading... </div>;
  }
}

export default TradeBlasterContainer;
