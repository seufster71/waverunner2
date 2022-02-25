/**
 *
 */
import { getHost } from "../app";

export function inputChange(field, value) {
  return function (dispatch) {
    let params = {};
    params.field = field;
    params.value = value;
    dispatch({ type: "TRADEBLASTER_INPUT_CHANGE", params });
  };
}

export function list() {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "LIST";
    params.requestParams.service = "TRADEBLASTER";
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
        dispatch({ type: "TRADEBLASTER_LIST", responseJson });
        if (info != null) {
          dispatch({ type: "SHOW_STATUS", info: info });
        }
      })
      .catch(function (error) {});
  };
}

export function addItem() {
  return function (dispatch) {
    dispatch({ type: "TRADEBLASTER_ADD_ITEM" });
  };
}

export function cancelItem() {
  return function (dispatch) {
    dispatch({ type: "TRADEBLASTER_CANCEL_ITEM" });
  };
}

export function historicalDetail(item) {
  return function (dispatch) {
    dispatch({ type: "TRADEBLASTER_HISTORICALDETAIL" , action: item});
  };
}

export function modifyItem(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "ITEM";
    params.requestParams.service = "TRADEBLASTER";
    params.requestParams.ITEMID = item.id;
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
        dispatch({ type: "TRADEBLASTER_MODIFY_ITEM", responseJson });
        if (info != null) {
          dispatch({ type: "SHOW_STATUS", info: info });
        }
      })
      .catch(function (error) {});
  };
}

export function backTest(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "ITEM";
    params.requestParams.service = "TRADEBLASTER";
    params.requestParams.ITEMID = item.id;
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
        dispatch({ type: "TRADEBLASTER_BACKTEST", responseJson });
        if (info != null) {
          dispatch({ type: "SHOW_STATUS", info: info });
        }
      })
      .catch(function (error) {});
  };
}


export function deleteItem(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "DELETE";
    params.requestParams.service = "TRADEBLASTER";
    params.requestParams.ITEMID = item.id;

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
        dispatch({type: "TRADEBLASTER_ITEM_LIST", responseJson});
        if (info != null) {
          dispatch({ type: "SHOW_STATUS", info: info });
        }
      })
      .catch(function (error) {});
  };
}

export function deleteBacktest(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "DELETE_BACKTEST";
    params.requestParams.service = "TRADEBLASTER";
    params.requestParams.ITEMID = item.id;

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
        dispatch({type: "TRADEBLASTER_BACKTEST_LIST", responseJson});
        if (info != null) {
          dispatch({ type: "SHOW_STATUS", info: info });
        }
      })
      .catch(function (error) {});
  };
}

export function saveItem(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "SAVE";
    params.requestParams.service = "TRADEBLASTER";
    params.requestParams.ITEM = item;

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
        if (
          responseJson != null &&
          responseJson.status != null &&
          responseJson.status == "SUCCESS"
        ) {
          dispatch(list());
        } else if (
          responseJson != null &&
          responseJson.status != null &&
          responseJson.status == "ACTIONFAILED"
        ) {
          dispatch({ type: "SHOW_STATUS", error: responseJson.errors });
        }
      })
      .catch(function (error) {});
  };
}
export function swingTradeBacktest(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "SWING_TRADE_BACKTEST";
    params.requestParams.service = "BACKTEST"
    params.requestParams.ITEM = item;

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
        dispatch(list());
      })
      .catch(function (error) {});
  };
}
export function dayTradeBacktest(item) {
  return function (dispatch) {
    let params = {};
    params.requestParams = {};
    params.requestParams.action = "DAY_TRADE_BACKTEST";
    params.requestParams.service = "BACKTEST"
    params.requestParams.ITEM = item;

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
        } else {
          return response.json();
        }
      })
      .then(() => {
        dispatch(list());
      })
      .catch(function () {});
  };
}
