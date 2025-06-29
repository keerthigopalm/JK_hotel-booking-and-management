export const ProtectedRoute = ({element : Component }) => {
    const location = useLocation();

    return ApiService.isAthenticated() ? (
        Component
    )  :  (
        <Navigate to="/login" replace state= {{ from: location }}/>
    );
};