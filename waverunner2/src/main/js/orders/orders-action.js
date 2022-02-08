/**
 *
 */
import { getHost } from "../app";

export function inputAmountChange(field, value) {
  return function (dispatch) {
    let params = {};
    params.field = field;
    params.value = value;
    dispatch({ type: "AMOUNT_INPUT_CHANGE", params });
  };
}

export function placeOrder(value, amountValue) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.service = "PLACE_ORDER";
    params.requestParams.action = "DEFAULT_ORDER";
    params.requestParams.stockName = value;
    params.requestParams.orderAmount = amountValue;
    params.URI = "/api/public/callService";

    const uri = getHost() + params.URI;
    let headers = new Headers();
    headers.set("Content-type", "application/json");
    if (params.auth != null) {
      headers.set("Authorization", "Basic " + params.auth);
    }
    fetch(uri, {
      method: "POST",
      credentials: "same-origin",
      headers: headers,
      body: JSON.stringify({ params: params.requestParams }),
    })
      .then(function (response) {
        if (response.status >= 400) {
          let responseMsg = { status: "ERROR", protocalError: response.status };
        } else {
          return response.json();
        }
      })
      .then((responseJson) => {
        dispatch({ type: "STOCK_BUY_REQUEST", responseJson });
      })
      .catch(function (error) {});
  };
}

export function trailingStopOrder(value, amountValue) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.service = "PLACE_ORDER";
    params.requestParams.action = "TRAILING_STOP_ORDER";
    params.requestParams.stockName = value;
    params.requestParams.orderAmount = amountValue;
    params.URI = "/api/public/callService";

    const uri = getHost() + params.URI;
    let headers = new Headers();
    headers.set("Content-type", "application/json");
    if (params.auth != null) {
      headers.set("Authorization", "Basic " + params.auth);
    }
    fetch(uri, {
      method: "POST",
      credentials: "same-origin",
      headers: headers,
      body: JSON.stringify({ params: params.requestParams }),
    })
      .then(function (response) {
        if (response.status >= 400) {
          let responseMsg = { status: "ERROR", protocalError: response.status };
        } else {
          return response.json();
        }
      })
      .then((responseJson) => {
        dispatch({ type: "TRAILING_STOP_ORDER_REQUEST", responseJson });
      })
      .catch(function (error) {});
  };
}


