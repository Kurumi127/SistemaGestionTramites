import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";

import Login from "../pages/auth/Login";

import AdminLayout from "../layouts/AdminLayout";
import OperadorLayout from "../layouts/OperadorLayout";

import DashboardAdmin from "../pages/admin/DashboardAdmin";
import DashboardOperador from "../pages/operador/DashboardOperador";

import PagePlaceholder from "../components/PagePlaceholder";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />

        <Route path="/login" element={<Login />} />

        <Route path="/admin" element={<AdminLayout />}>
          <Route path="dashboard" element={<DashboardAdmin />} />

          <Route
            path="usuarios"
            element={
              <PagePlaceholder
                titulo="Usuarios"
                descripcion="Aquí se administrarán los usuarios y operadores del sistema."
              />
            }
          />

          <Route
            path="areas"
            element={
              <PagePlaceholder
                titulo="Áreas"
                descripcion="Aquí se administrarán las áreas de servicio."
              />
            }
          />

          <Route
            path="asignaciones"
            element={
              <PagePlaceholder
                titulo="Asignaciones"
                descripcion="Aquí se asignarán áreas autorizadas a los operadores."
              />
            }
          />

          <Route
            path="servicios"
            element={
              <PagePlaceholder
                titulo="Servicios"
                descripcion="Aquí se administrará el catálogo de servicios."
              />
            }
          />

          <Route
            path="solicitudes"
            element={
              <PagePlaceholder
                titulo="Solicitudes"
                descripcion="Aquí se consultarán las solicitudes registradas."
              />
            }
          />

          <Route
            path="reportes"
            element={
              <PagePlaceholder
                titulo="Reportes"
                descripcion="Aquí se consultarán los reportes y dashboards administrativos."
              />
            }
          />
        </Route>

        <Route path="/operador" element={<OperadorLayout />}>
          <Route path="dashboard" element={<DashboardOperador />} />

          <Route
            path="areas"
            element={
              <PagePlaceholder
                titulo="Mis áreas"
                descripcion="Aquí el operador consultará sus áreas autorizadas."
              />
            }
          />

          <Route
            path="servicios"
            element={
              <PagePlaceholder
                titulo="Servicios del área"
                descripcion="Aquí el operador consultará los servicios de sus áreas autorizadas."
              />
            }
          />

          <Route
            path="mis-servicios"
            element={
              <PagePlaceholder
                titulo="Mis servicios"
                descripcion="Aquí el operador marcará y consultará sus servicios propios."
              />
            }
          />

          <Route
            path="solicitudes"
            element={
              <PagePlaceholder
                titulo="Mis solicitudes"
                descripcion="Aquí el operador consultará y atenderá sus solicitudes."
              />
            }
          />
        </Route>

        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;