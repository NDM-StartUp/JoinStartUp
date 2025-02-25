import React, { useEffect, useState } from "react";
import axios from "../../utils/axiosInstance.tsx";
import { useSearchParams } from "react-router-dom";

interface Startup {
    id: number;
    name: string;
    companyName: string;
    description: string;
    requirements: string;
    location: string;
    isPaid: boolean;
}

export const FindStartups: React.FC = () => {
    const [startups, setStartups] = useState<Startup[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [searchParams, setSearchParams] = useSearchParams();

    const [filters, setFilters] = useState({
        startUpId: "",
        companyName: "",
        location: "",
        isPaid: "",
    });

    useEffect(() => {
        fetchStartups();
    }, [searchParams]);

    const fetchStartups = async () => {
        setLoading(true);
        try {
            const params = Object.fromEntries(searchParams.entries());
            const response = await axios.get<Startup[]>("/startUp/search", { params });
            setStartups(response.data);
            setError(null);
        } catch (err) {
            setError("Error fetching startups");
        } finally {
            setLoading(false);
        }
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFilters((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSearch = () => {
        const newParams = new URLSearchParams();
        if (filters.startUpId) newParams.append("startUpId", filters.startUpId);
        if (filters.companyName) newParams.append("companyName", filters.companyName);
        if (filters.location) newParams.append("location", filters.location);
        if (filters.isPaid) newParams.append("isPaid", filters.isPaid);

        setSearchParams(newParams);
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div style={{ padding: "20px" }}>
            <h1>Find Startups</h1>

            <div style={{ marginBottom: "20px", padding: "10px", border: "1px solid #ccc" }}>
                <input
                    type="text"
                    name="startUpId"
                    placeholder="Startup ID"
                    value={filters.startUpId}
                    onChange={handleInputChange}
                    style={{ marginRight: "10px" }}
                />
                <input
                    type="text"
                    name="companyName"
                    placeholder="Company Name"
                    value={filters.companyName}
                    onChange={handleInputChange}
                    style={{ marginRight: "10px" }}
                />
                <input
                    type="text"
                    name="location"
                    placeholder="Location"
                    value={filters.location}
                    onChange={handleInputChange}
                    style={{ marginRight: "10px" }}
                />
                <select name="isPaid" value={filters.isPaid} onChange={handleInputChange} style={{ marginRight: "10px" }}>
                    <option value="">Paid?</option>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                </select>
                <button onClick={handleSearch}>Search</button>
            </div>

            {error ? <p>{error}</p> : (
                <div>
                    {startups.length > 0 ? (
                        <ul>
                            {startups.map((startup) => (
                                <li key={startup.id}>
                                    <h2>{startup.name}</h2>
                                    <p><b>Company:</b> {startup.companyName}</p>
                                    <p><b>Description:</b> {startup.description}</p>
                                    <p><b>Requirements:</b> {startup.requirements}</p>
                                    <p><b>Location:</b> {startup.location}</p>
                                    <p><b>Paid:</b> {startup.isPaid ? "Yes" : "No"}</p>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No startups found.</p>
                    )}
                </div>
            )}
        </div>
    );
};