import { useState } from "react";
import Customer from "../types/Customer";
import Log from "../types/Log";
import Status from "../types/Status";
import "./ControlPanel.css";

// Web page event handlers
const DBNAME = "customer_db";

/**
 * Clear all customer data from the database
 */
const clearDB = (
  status: Status,
  log: Log,
  setCustomers: (customerData: any) => void
) => {
  console.log("Delete all rows from the Customers database");
  let customer = new Customer(DBNAME, status, log);
  customer.removeAllRows();
  setCustomers([]);
};

/**
 * Add customer data to the database
 */
const loadDB = (status: Status, log: Log) => {
  log("Load the Customers database");

  // Customers to add to initially populate the database with
  const customerData = [
    { userid: "444", name: "Bill", email: "bill@company.com" },
    { userid: "555", name: "Donna", email: "donna@home.org" },
  ];
  let customer = new Customer(DBNAME, status, log);
  customer.initialLoad(customerData);
};

/**
 * Query customer data to the database
 */
const queryDB = (
  status: Status,
  log: Log,
  setCustomers: (customerData: any) => void
) => {
  log("Query the Customers database");
  let customer = new Customer(DBNAME, status, log);
  customer.queryAllRows((customerData: any) => {
    const list = customerData as any[];
    if (!list || list.length === 0) {
      log("No customer data");
      setCustomers([]);
      return;
    }

    for (const customer of list) {
      log(JSON.stringify(customer));
    }
    setCustomers(list);
  });
};

function ControlPanel() {
  const [status, setStatus] = useState("Status messages will display here...");
  const [logs, setLogs] = useState("Log messages will display here...");
  const [customers, setCustomers] = useState([] as any[]);
  const log = (message: string) => {
    setLogs((l) => `${l}\r\n${message}`);
  };

  return (
    <div className="controlPanel">
      <div className="row buttons">
        <button onClick={() => loadDB(setStatus, log)}>Load DB</button>
        <button onClick={() => queryDB(setStatus, log, setCustomers)}>
          Query DB
        </button>
        <button onClick={() => clearDB(setStatus, log, setCustomers)}>
          Clear DB
        </button>
      </div>
      <div className="row status">
        <span>{status}</span>
      </div>
      <div className="row logs">
        <div className="row">
          <pre>{logs}</pre>
          <table>
            <tr>
              <th>User Id</th>
              <th>Name</th>
              <th>Email</th>
            </tr>
            {customers.map((customer) => (
              <tr>
                <td>{customer.userid}</td>
                <td>{customer.name}</td>
                <td>{customer.email}</td>
              </tr>
            ))}
          </table>
        </div>
      </div>
    </div>
  );
}

export default ControlPanel;
