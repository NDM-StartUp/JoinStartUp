import {Link} from "react-router-dom";

export const Navbar = () => {
    return (
        <header>
            <nav style={{ padding: "10px", display: "flex", gap: "20px" }}>
                <Link to="/feed">JoinStartUp</Link>
                <Link to="#">Connections</Link>
                <Link to="#">Find StartUps</Link>
                <Link to="/profile">Profile</Link>
            </nav>
        </header>

    )
}