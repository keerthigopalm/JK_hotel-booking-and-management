import React from "react";
import {Navicate,useLocation} from 'react-router-dom';
import ApiService from './ApiServise';

export const ProtectedRoute = ({element : Component }) => {
    
    const location = useLocation();

    return ApiService.isAthenticated() ? (
        Component
    )  :  (
        <Navigate to="/login" replace state= {{ from: location }}/>
    );
};


export const AdminRoute = ({ element : component}) => {
    const location = useLocation();

    return ApiService.isAdmin() ? (
        component
    ) : (
        <Navigate ti="/login" replace state={{ from: location}} />
    );
};