import Log from "./Log";
import Status from "./Status";

class Customer {
  private dbName: string;
  private status: Status;
  private log: Log;

  constructor(dbName: string, status: Status, log: Log) {
    this.dbName = dbName;
    this.status = status;
    this.log = log;

    if (!window.indexedDB) {
      this.log(
        "Your browser doesn't support a stable version of IndexedDB. \
        Such and such feature will not be available."
      );
    }
  }

  /**
   * Remove all rows from the database
   * @memberof Customer
   */
  removeAllRows() {
    this.status("Clear in-progress");
    const request = indexedDB.open(this.dbName, 1);

    request.onerror = (event: Event) => {
      const target = event.target as IDBOpenDBRequest;

      if (!target.error) {
        this.log("onerror did not receive a target error");
        return;
      }

      this.log(`removeAllRows - Database error: ${target.error.message}`);
    };

    request.onsuccess = (event: Event) => {
      this.log("Deleting all customers...");

      const target = event.target as IDBOpenDBRequest;

      if (!target.result) {
        this.log("onsuccess did not receive a target result");
        return;
      }

      const db = target.result;
      const txn = db.transaction("customers", "readwrite");
      txn.onerror = (event: any) => {
        this.log(
          `removeAllRows - Txn error: ${event.target.error.code} - ${event.target.error.message}`
        );
      };
      txn.oncomplete = (event: any) => {
        this.log("All rows removed!");
        this.status("Clear completed");
      };
      const objectStore = txn.objectStore("customers");
      const getAllKeysRequest = objectStore.getAllKeys();
      getAllKeysRequest.onsuccess = (event: any) => {
        getAllKeysRequest.result.forEach((key: any) => {
          objectStore.delete(key);
        });
      };
    };
  }

  /**
   * Populate the Customer database with an initial set of customer data
   * @param {[object]} customerData Data to add
   * @memberof Customer
   */
  initialLoad(customerData: any) {
    this.status("Load in-progress");
    const request = indexedDB.open(this.dbName, 1);

    request.onerror = (event) => {
      const target = event.target as IDBOpenDBRequest;

      if (!target.error) {
        this.log("onerror did not receive a target error");
        return;
      }

      this.log(`initialLoad - Database error: ${target.error.message}`);
    };

    const populate = (objectStore: IDBObjectStore) => {
      this.log("Populating customers...");

      // Populate the database with the initial set of rows
      customerData.forEach(function (customer: any) {
        objectStore.put(customer);
      });
    };
    request.onsuccess = (event) => {
      const target = event.target as IDBOpenDBRequest;

      if (!target.result) {
        this.log("onsuccess did not receive a target result");
        return;
      }

      const db = target.result;
      const txn = db.transaction("customers", "readwrite");
      txn.onerror = (event: any) => {
        this.log(
          `initialLoad - Txn error: ${event.target.error.code} - ${event.target.error.message}`
        );
      };
      txn.oncomplete = (event: any) => {
        this.log("Added customer data!");
        this.status("Load completed");
      };
      const objectStore = txn.objectStore("customers");
      populate(objectStore);
      db.close();
    };
    request.onupgradeneeded = (event) => {
      const target = event.target as IDBOpenDBRequest;

      if (!target.result) {
        this.log("onsuccess did not receive a target result");
        return;
      }

      const db = target.result;

      const objectStore = db.createObjectStore("customers", {
        keyPath: "userid",
      });

      // Create an index to search customers by name and email
      objectStore.createIndex("name", "name", { unique: false });
      objectStore.createIndex("email", "email", { unique: true });

      // Populate
      populate(objectStore);

      db.close();

      this.status("Load completed");
    };
  }

  /**
   * Query the Customer database
   * @param {(customerData: any) => void} callback to send results
   */
  queryAllRows(callback: (customerData: any) => void) {
    this.status("Query in-progress");
    const request = indexedDB.open(this.dbName, 1);

    request.onerror = (event: Event) => {
      const target = event.target as IDBOpenDBRequest;

      if (!target.error) {
        this.log("onerror did not receive a target error");
        return;
      }

      this.log(`queryAllRows - Database error: ${target.error.message}`);
    };

    request.onsuccess = (event: Event) => {
      this.log("Querying all customers...");

      const target = event.target as IDBOpenDBRequest;

      if (!target.result) {
        this.log("onsuccess did not receive a target result");
        return;
      }

      const db = target.result;
      const txn = db.transaction("customers", "readonly");
      txn.onerror = (event: any) => {
        this.log(
          `queryAllRows - Txn error: ${event.target.error.code} - ${event.target.error.message}`
        );
      };
      txn.oncomplete = (event: any) => {
        this.log("All rows queried!");
        this.status("Query completed");
      };
      const objectStore = txn.objectStore("customers");
      const getAllRequest = objectStore.getAll();
      getAllRequest.onsuccess = (event: any) => {
        callback(getAllRequest.result);
      };
    };
  }
}

export default Customer;
