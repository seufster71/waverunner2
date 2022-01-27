import {combineReducers} from 'redux';
import appPrefs from './apppref-reducer';
import appMenus from './appmenu-reducer';
import session from './session-reducer';
import status from './status-reducer';
import stocks from '../stocks/stocks-reducer';
import crypto from '../crypto/crypto-reducer';
import dashboard from '../dashboard/dashboard-reducer';
import tradeblaster from '../tradeblaster/tradeblaster-reducer';

const rootReducer = combineReducers({appPrefs,appMenus,session,status,stocks,crypto,dashboard,tradeblaster});

export default rootReducer;
