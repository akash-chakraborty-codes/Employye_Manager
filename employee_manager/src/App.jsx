import { useEffect, useState } from "react";
import "./App.css";

const API_URL = "http://localhost:8080/api/employees";

// DEMO DATA (shown until backend responds)
const DEMO_EMPLOYEES = [
  { id: 1, firstname: "John", lastName: "Doe", email: "john@example.com" },
  { id: 2, firstname: "Jane", lastName: "Smith", email: "jane@example.com" },
];

export default function EmployeeApp() {
  const [employees, setEmployees] = useState(DEMO_EMPLOYEES);
  const [form, setForm] = useState({ firstName: "", lastName: "", email: "" });
  const [editingEmployee, setEditingEmployee] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [darkMode, setDarkMode] = useState(true);
  const [search, setSearch] = useState("");

  // Load backend data
  useEffect(() => {
    fetch(API_URL)
      .then((res) => res.json())
      .then((data) => setEmployees(data))
      .catch(() => console.log("Using demo data"));
  }, []);

  // Filtered employees
  const filteredEmployees = employees.filter((e) =>
    `${e.firstname} ${e.lastName}`.toLowerCase().includes(search.toLowerCase())
  );

  // Form handling
  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const openAdd = () => {
    setForm({ firstName: "", lastName: "", email: "" });
    setEditingEmployee(null);
    setShowModal(true);
  };

  const openEdit = (emp) => {
    setForm({
      firstName: emp.firstname,
      lastName: emp.lastName,
      email: emp.email,
    });
    setEditingEmployee(emp);
    setShowModal(true);
  };

  const saveEmployee = () => {
    const method = editingEmployee ? "PUT" : "POST";
    const payload = {
      id: editingEmployee ? editingEmployee.id : null,
      firstname: form.firstName,
      lastName: form.lastName,
      email: form.email,
    };

    fetch(API_URL, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    })
      .then(async (res) => {
        if (!res.ok) throw new Error("Save failed");
        const text = await res.text();
        return text ? JSON.parse(text) : null;
      })
      .then((data) => {
        if (data) {
          setEmployees((prev) =>
            editingEmployee
              ? prev.map((e) => (e.id === data.id ? data : e))
              : [...prev, data]
          );
        }
        setShowModal(false);
      })
      .catch((err) => console.error("SAVE ERROR:", err));
  };

  const deleteEmployee = (id) => {
    fetch(`${API_URL}/${id}`, { method: "DELETE" })
      .then(() => setEmployees((prev) => prev.filter((e) => e.id !== id)))
      .catch((err) => console.error(err));
  };

  return (
    <div className={`app ${darkMode ? "dark" : "light"}`}>
      {/* HEADER */}
      <div className="header">
        <h1>Employee Manager</h1>
        <div className="right-controls">
          <input
            type="text"
            placeholder="Search..."
            className="search"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <button className="toggle" onClick={() => setDarkMode(!darkMode)}>
            {darkMode ? "Light Mode" : "Dark Mode"}
          </button>
          <button className="add-btn" onClick={openAdd}>
            Add Employee
          </button>
        </div>
      </div>

      {/* CARDS */}
      <div className="card-container">
        {filteredEmployees.length === 0 && (
          <p className="no-data">No Employees Found</p>
        )}
        {filteredEmployees.map((emp) => (
          <div className="card" key={emp.id}>
            <h2>
              {emp.firstname} {emp.lastName}
            </h2>
            <p className="email">{emp.email}</p>
            {!emp.id && <span className="demo-tag">DEMO</span>}
            <div className="card-actions">
              <button onClick={() => openEdit(emp)}>Edit</button>
              <button className="danger" onClick={() => deleteEmployee(emp.id)}>
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* MODAL */}
      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <h2>{editingEmployee ? "Edit" : "Add"} Employee</h2>
            <input
              name="firstName"
              placeholder="First Name"
              value={form.firstName}
              onChange={handleChange}
            />
            <input
              name="lastName"
              placeholder="Last Name"
              value={form.lastName}
              onChange={handleChange}
            />
            <input
              name="email"
              placeholder="Email"
              value={form.email}
              onChange={handleChange}
            />
            <div className="modal-actions">
              <button className="primary" onClick={saveEmployee}>
                Save
              </button>
              <button onClick={() => setShowModal(false)}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
