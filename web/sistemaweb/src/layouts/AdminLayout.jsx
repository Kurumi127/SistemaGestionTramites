import { NavLink, Navigate, Outlet, useNavigate } from "react-router-dom";
import "./Layout.css";

function AdminLayout() {
  const navigate = useNavigate();
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  if (!usuario) {
    return <Navigate to="/login" replace />;
  }

  if (usuario.tipoUsuario !== "ADMINISTRADOR") {
    return <Navigate to="/login" replace />;
  }

  const cerrarSesion = () => {
    localStorage.removeItem("usuario");
    navigate("/login");
  };

  return (
    <div className="layout">
      <aside className="sidebar">
        <h2>Gestión de Trámites</h2>

        <p className="usuario">
          {usuario.nombre}
          <span>{usuario.tipoUsuario}</span>
        </p>

        <nav className="menu">
          <NavLink to="/admin/dashboard">Dashboard</NavLink>
          <NavLink to="/admin/usuarios">Usuarios</NavLink>
          <NavLink to="/admin/areas">Áreas</NavLink>
          <NavLink to="/admin/asignaciones">Asignaciones</NavLink>
          <NavLink to="/admin/servicios">Servicios</NavLink>
          <NavLink to="/admin/solicitudes">Solicitudes</NavLink>
          <NavLink to="/admin/reportes">Reportes</NavLink>
        </nav>

        <button className="logout" onClick={cerrarSesion}>
          Cerrar sesión
        </button>
      </aside>

      <main className="contenido">
        <Outlet />
      </main>
    </div>
  );
}

export default AdminLayout;