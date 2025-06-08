import { useState } from "react";
import { Button } from "@radix-ui/themes";
import { backendHealthCheck, mongodbHealthCheck, postgresHealthCheck } from "@network/health-check.ts";

const CheckBackendHealth = () => {
    const [messages, setMessages] = useState<string[]>([]);

    const triggerHealthCheck = async () => {
        setMessages([]);
        const backendResponse = await backendHealthCheck();
        setMessages((prev) => [ `Backend: ${backendResponse ? "Healthy" : "Unhealthy"}`, ...prev]);

        const mongodbResponse = await mongodbHealthCheck();
        setMessages((prev) => [ `MongoDB: ${mongodbResponse ? "Healthy" : "Unhealthy"}`,...prev]);

        const postgresResponse = await postgresHealthCheck();
        setMessages((prev) => [ `Postgres: ${postgresResponse ? "Healthy" : "Unhealthy"}`,...prev, "", "",""]);
    };

    return (
        <div>
            <Button onClick={triggerHealthCheck}>Trigger Health Check!</Button>
            <div style={{ border: "1px solid black", padding: "10px", marginTop: "10px" }}>
                {messages.map((message, index) => (
                    <p key={index} style={{ margin: "5px 0" }}>{message}</p>
                ))}
            </div>
        </div>
    );
};

export default CheckBackendHealth;