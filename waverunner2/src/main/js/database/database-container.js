import React from "react";
import DatabaseView from "./view/database-view";
import * as databaseActions from "./database-actions";


function backload(){
    databaseActions.backload();
};

export default function DatabaseContainer() {
  return <DatabaseView backload={backload} />;
}
