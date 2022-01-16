import React from "react";
import { Routes, Route} from "react-router-dom";
import DashboardContainer from "./dashboard/dashboard-container";
import CryptoContainer from "./crypto/crypto-container";
import StocksContainer from "./stocks/stocks-container";

function PageContainer() {

      return (
        <Routes>
      		<Route exact path="/" element={<DashboardContainer />}/>	
			<Route path="/crypto/*" element={<CryptoContainer />}/>	
			<Route path="/stocks/*" element={<StocksContainer />}/>	
      	</Routes>
      );
}

export default PageContainer;
