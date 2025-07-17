import React from 'react';
import {
  Grid,
  Typography,
  Button,
  Box,
  TextField,
  Paper,
  useMediaQuery,
  useTheme,
} from '@mui/material';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';

const HeroSection = () => {
  const theme = useTheme();
  const isSmall = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <Box sx={{ backgroundColor: '#f9f9ff', py: 10, px: { xs: 2, md: 10 } }}>
      <Grid container spacing={6} alignItems="flex-start">
        {/* Left Side */}
        <Grid item xs={12} md={7} sx={{ pr: { md: 6, xs: 0 } }}>
          <Box sx={{ maxWidth: '600px' }}>
            <Typography variant="subtitle2" sx={{ color: '#5932EA', fontWeight: 600, mb: 1.5 }}>
              Welcome to ASSET SPHERE
            </Typography>

            <Typography variant="h3" sx={{ fontWeight: 700, color: '#1e1b2e', mb: 2 }}>
              All-in-One Asset Management Platform
            </Typography>

            <Typography
              variant="body1"
              sx={{ fontSize: '1.1rem', color: '#475569', mb: 3, lineHeight: 1.8 }}
            >
              Simplify how you track, maintain, and optimize your assets. From acquisition to
              disposal, <strong style={{ color: '#5932EA' }}>Asset Sphere</strong> helps you
              eliminate downtime, reduce maintenance costs, and maximize asset lifetime. It optimizes your entire asset life cycle from procurement to disposal, and
              maximizes your ROI — all in one platform.
            </Typography>

            <Box sx={{ display: 'flex', gap: 2, flexWrap: 'wrap' }}>
              <Button
                variant="contained"
                sx={{
                  backgroundColor: '#5932EA',
                  textTransform: 'none',
                  px: 3,
                  '&:hover': { backgroundColor: '#4a29c9' },
                }}
              >
                Know More
              </Button>
              <Button
                variant="outlined"
                startIcon={<PlayCircleOutlineIcon />}
                sx={{
                  color: '#5932EA',
                  borderColor: '#5932EA',
                  textTransform: 'none',
                  px: 3,
                  '&:hover': {
                    backgroundColor: '#f1f0ff',
                    borderColor: '#4a29c9',
                  },
                }}
              >
                View Demo
              </Button>
            </Box>
          </Box>
        </Grid>

        {/* Right Side - Form */}
        <Grid item xs={12} md={5}>
          <Paper
            elevation={3}
            sx={{
              p: 4,
              borderRadius: 3,
              maxWidth: 450,
              mx: isSmall ? 'auto' : 0,
              backgroundColor: '#fff',
            }}
          >
            <Typography variant="h6" sx={{ fontWeight: 600, mb: 1 }}>
              <center>Start your free trial</center>
            </Typography>
            <Typography variant="body2" sx={{ mb: 3, color: '#64748b' }}>
              <center>Fill in your details and we’ll get back to you shortly.</center>
            </Typography>

            <Box component="form" noValidate autoComplete="off">
              <TextField
                label="Name"
                fullWidth
                variant="outlined"
                size="small"
                sx={{ mb: 2 }}
              />
              <TextField
                label="Email"
                fullWidth
                variant="outlined"
                size="small"
                sx={{ mb: 2 }}
              />
              <TextField
                label="Organization"
                fullWidth
                variant="outlined"
                size="small"
                sx={{ mb: 3 }}
              />
              <Button
                fullWidth
                variant="contained"
                sx={{
                  backgroundColor: '#5932EA',
                  textTransform: 'none',
                  '&:hover': {
                    backgroundColor: '#4a29c9',
                  },
                }}
              >
                Request Trial
              </Button>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default HeroSection;