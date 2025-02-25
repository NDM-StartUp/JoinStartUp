import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";

export const Navbar = () => {
    const [searchQuery, setSearchQuery] = useState("");
    const navigate = useNavigate();

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        if (searchQuery.trim()) {
            navigate(`#=${searchQuery}`);
        }
    };

    return (
        <header>
            <nav style={{ padding: "10px", display: "flex", gap: "20px" }}>
                <Link to="/feed">JoinStartUp</Link>

                <form onSubmit={handleSearch} style={{ marginLeft: "auto" }}>
                    <input
                        type="text"
                        placeholder="Search something..."
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        style={{ padding: "5px" }}
                    />
                    <button type="submit" style={{ marginLeft: "5px" }}>Search</button>
                </form>

                <Link to="#">Connections</Link>
                <Link to="/findStartUp">Find StartUps</Link>
                <Link to="/profile">Profile</Link>
            </nav>
        </header>

    )
}