import React from "react";
import { Container, Typography, Box } from "@mui/material";
import '../../styles/Features.css';

const Features = () => {
  return (
    <Box className="features-section">
      <Container maxWidth="lg">
        <Typography
          variant="h5"
          align="center"
          sx={{ color: "#5932EA", fontWeight: 600, mb: 2 }}
        >
          What are the Features of Asset Hub’s Asset Management Software
        </Typography>

        <Typography
          variant="body1"
          align="center"
          sx={{ fontSize: "1.05rem", color: "#444", mb: 2, maxWidth: "900px", mx: "auto", lineHeight: 1.8 }}
        >
          Asset Sphere's Asset Management Software Solution empowers your maintenance team to perform
          at their best. Reduce maintenance costs, improve productivity, and optimize operations.
        </Typography>

        <Typography
          variant="body2"
          align="center"
          sx={{ fontWeight: "bold", color: "#7e57c2", mb: 5, cursor: "pointer" }}
        >
          Explore All Features &gt;
        </Typography>

        <Box className="feature-wrapper">
          <Box className="feature-img">
            <img src="/feature.png" alt="Financial Management" />
          </Box>
          <Box className="feature-text">
            <Typography
              variant="h6"
              sx={{ color: "#5932EA", fontWeight: 600, mb: 1 }}
            >
              Financial Management
            </Typography>
            <Typography
              variant="body2"
              sx={{ fontSize: "1.05rem", color: "#444", lineHeight: 1.8 }}
            >
              With Asset Sphere, track spending, reduce downtime costs, and forecast asset expenses
              across your organization. Our platform helps streamline financial tracking and
              reporting for all your equipment. You can allocate budgets effectively, monitor
              real-time usage trends, and get insights to improve ROI — all from a centralized dashboard.
            </Typography>
          </Box>
        </Box>
      </Container>
    </Box>
  );
};

export default Features;