import React, { useEffect, useState, useContext } from "react";
import axios from "../../utils/axiosInstance.tsx";
import { AuthContext } from "../../contexts/authContext.tsx";

interface Startup {
  id: number;
  name: string;
  companyName: string;
  description: string;
  requirements: string;
  location: string;
  isPaid: boolean;
}

interface UserTypes {
  isEmployee: boolean;
  isEmployer: boolean;
}

export const Feed: React.FC = () => {
  const { user } = useContext(AuthContext);
  const [startups, setStartups] = useState<Startup[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [becomingEmployer, setBecomingEmployer] = useState(false);
  const [userTypes, setUserTypes] = useState<UserTypes | null>(null);

  useEffect(() => {
    fetchStartups();
    checkUserRole();
  }, []);

  const fetchStartups = async () => {
    setLoading(true);
    try {
      const response = await axios.get<Startup[]>("/startUp/all");
      setStartups(response.data);
      setError(null);
    } catch (err) {
      setError("Error fetching startups");
    } finally {
      setLoading(false);
    }
  };

  const checkUserRole = async () => {
    if (!user || !user.id) return;
    try {
      const response = await axios.get<UserTypes>(`/users/${user.id}/user-types`);
      setUserTypes(response.data);
    } catch (err) {
      console.error("Error checking user role:", err);
    }
  };

  const becomeEmployer = async () => {
    if (!user || !user.id) {
      setError("User not authenticated.");
      return;
    }

    setBecomingEmployer(true);
    try {
      await axios.post(`/users/${user.id}/add-role?addEmployerRole=true`);
      alert("🎉 Congratulations! You are now a Startup Creator!");
      window.location.reload();
    } catch (err) {
      setError("Error updating role. You may already be an Employer.");
    } finally {
      setBecomingEmployer(false);
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
      <div style={{ padding: "20px" }}>
        <h1>Your recent applications</h1>
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
                  <p>No startups available.</p>
              )}
            </div>
        )}

        <h1>Personalized</h1>

        {userTypes?.isEmployee && !userTypes?.isEmployer && (
            <div style={{
              border: "1px solid #ccc",
              padding: "20px",
              textAlign: "center",
              marginTop: "40px"
            }}>
              <h2>Become a Startup Creator!</h2>
              <p>Take your career to the next level. Start your own startup!</p>

              <button
                  onClick={becomeEmployer}
                  disabled={becomingEmployer}
                  style={{ padding: "10px 20px", marginTop: "10px" }}
              >
                {becomingEmployer ? "Processing..." : "Start as Employer"}
              </button>
            </div>
        )}
      </div>
  );
};