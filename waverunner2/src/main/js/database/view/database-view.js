import React from "react";

export default function DatabaseView({backload}) {
  return (
    <div>
      <input
        type="submit"
        name="DatabaseButton"
        id="DatabaseButton"
        className="form-control"
        value="Load Database"
        onClick = {backload}
      />
    </div>
  );
}
