import {AuthContext} from "../../contexts/authContext.tsx";
import React, {ReactNode, useContext} from "react";

interface ProtectedRouteProps {
    children: ReactNode;
    roles?: string[];
}

export const ProtectedComponent : React.FC<ProtectedRouteProps> = ({children, roles}) => {
    const authContext = useContext(AuthContext);

    if (!authContext) {
        return null;
    }

    if (authContext.isLoading) {
        return <div>Loading...</div>;
    }

    if (!authContext.user || roles && !authContext.user.roles.some(userRole => roles.includes(userRole))) {
        return <></>;
    }

    return <>{children}</>;
}