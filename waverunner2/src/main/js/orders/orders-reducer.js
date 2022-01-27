/**
 *
 */
export default function ordersReducer(state = {}, action) {
  let myState = {};
  switch (action.type) {
    case "AMOUNT_INPUT_CHANGE": {
      if (action.params != null) {
        let clone = Object.assign({}, state);
        clone.test_field = action.params.value;
        return clone;
      } else {
        return state;
      }
    }

    default:
      return state;
  }
}
