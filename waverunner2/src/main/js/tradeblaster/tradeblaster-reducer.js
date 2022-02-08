/**
 * 
 */
 export default function tradeBlasterReducer(state = {}, action) {
	let myState = {};
	switch(action.type) {
		case 'TRADEBLASTER_INPUT_CHANGE': {
			if (action.params != null) {
				let item = {};
				if (state.item == null) {
					item[action.params.field] = action.params.value;
				} else {
					item = Object.assign({}, state.item);
					item[action.params.field] = action.params.value;
				}
				return Object.assign({}, state, {
					item: item
				});
			} else {
        		return state;
    		}
    	}
		case 'TRADEBLASTER_LIST': {
			if (action.responseJson != null && action.responseJson.params != null) {
				let itemCount = {};
  				if (action.responseJson.params.ITEMCOUNT != null) {
    				itemCount = action.responseJson.params.ITEMCOUNT;
  				}
				let items = {};
  				if (action.responseJson.params.ITEMS != null) {
    				items = action.responseJson.params.ITEMS;
  				}
				let backtestCount = {};
				if(action.responseJson.params.backtestCount != null){
					backtestCount = action.responseJson.params.backtestCount;
				}
				let backtests= {};
				if(action.responseJson.params.backtests != null){
					backtests = action.responseJson.params.backtests;
				}
				return Object.assign({}, state, {
					itemCount: itemCount,
					items: items,
					backtestCount: backtestCount,
					backtests: backtests,
					backtest: {},
					item: {},
					view: ""
				});
			
			} else {
        		return state;
    		}
		}

		case 'TRADEBLASTER_ITEM_LIST': {
			if (action.responseJson != null && action.responseJson.params != null) {
				let itemCount = {};
  				if (action.responseJson.params.ITEMCOUNT != null) {
    				itemCount = action.responseJson.params.ITEMCOUNT;
  				}
				let items = {};
  				if (action.responseJson.params.ITEMS != null) {
    				items = action.responseJson.params.ITEMS;
  				}
				return Object.assign({}, state, {
					itemCount: itemCount,
					items: items,
					item: {},
					view: ""
				});
			
			} else {
        		return state;
    		}
		}

		case 'TRADEBLASTER_BACKTEST_LIST': {
			if (action.responseJson != null && action.responseJson.params != null) {
				let backtestCount = {};
				if(action.responseJson.params.backtestCount != null){
					backtestCount = action.responseJson.params.backtestCount;
				}
				let backtests= {};
				if(action.responseJson.params.backtests != null){
					backtests = action.responseJson.params.backtests;
				}
				return Object.assign({}, state, {
					backtestCount: backtestCount,
					backtests: backtests,
					backtest: {},
					view: ""
				});
			
			} else {
        		return state;
    		}
		}


		case 'TRADEBLASTER_ADD_ITEM': {
			let clone = Object.assign({}, state);
			clone.view = "ADD";
			clone.item = {};
			return clone;
    	}
		case 'TRADEBLASTER_CANCEL_ITEM': {
			let clone = Object.assign({}, state);
			clone.view = "";
			clone.item = {};
			return clone;
    	}
		case 'TRADEBLASTER_MODIFY_ITEM': {
			if (action.responseJson != null && action.responseJson.params != null) {
				let item = {};
  				if (action.responseJson.params.item != null) {
    				item = action.responseJson.params.item;
  				}
				return Object.assign({}, state, {
					item: item,
					view: "MODIFY"
				});
			
			} else {
        		return state;
    		}
    	}
		case 'TRADEBLASTER_BACKTEST':{
			if (action.responseJson != null && action.responseJson.params != null) {
				let item = {};
  				if (action.responseJson.params.item != null) {
    				item = action.responseJson.params.item;
  				}
				return Object.assign({}, state, {
					item: item,
					view: "BACKTEST"
				});
			
			} else {
        		return state;
    		}
		}
		case 'TRADEBLASTER_SAVE_ITEM': {
			if (action.responseJson != null && action.responseJson.params != null) {
				let item = {};
  				if (action.responseJson.params.ITEM != null) {
    				item = action.responseJson.params.ITEM;
  				}
				return Object.assign({}, state, {
					item: item,
					view: ""
				});
			
			} else {
        		return state;
    		}
    	}
		default:
		return state;
	}
}