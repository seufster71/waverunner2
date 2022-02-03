export default function databaseReducer(state = {}, action) {
  let myState = {};
  switch (action.type) {
    case "BACKLOAD": {
      return state;
    }
    default:
      return state;
  }
}
