import React, { useContext, ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../contexts/authContext.tsx";

interface ProtectedRouteProps {
    children: ReactNode;
    roles?: string[];
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children, roles }) => {
    const authContext = useContext(AuthContext);

    if (!authContext) {
        return null;
    }

    if (authContext.isLoading) {
        return <div>Loading...</div>;
    }

    if (!authContext.user) {
        return <Navigate to="/login" replace />;
    }

    if (roles && !authContext.user.roles.some(userRole => roles.includes(userRole))) {
        return <Navigate to="/feed" replace />;
    }

    return <>{children}</>;
};

export default ProtectedRoute;