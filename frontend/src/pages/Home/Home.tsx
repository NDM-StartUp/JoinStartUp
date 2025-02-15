import {Link} from "react-router-dom";

export const Home = () => {
    return (
        <>
            <h1>Welcome to the Join start up:
            </h1>
            <Link to="/login">Login</Link>
            <br></br>
            <Link to="/register">Register</Link>
        </>
    )
}