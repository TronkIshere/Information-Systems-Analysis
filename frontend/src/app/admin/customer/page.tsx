"use client";

import React, { useState } from 'react';
import { 
  Container, 
  Typography, 
  Box, 
  Button, 
  TextField, 
  InputAdornment,
  Avatar,
  Chip,
  IconButton
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import BlockIcon from '@mui/icons-material/Block';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { createColumnHelper } from '@tanstack/react-table';
import CustomTable from '@/components/ui/table/CustomTable';

// User interface
interface User {
  id: string;
  name: string;
  email: string;
  company: string;
  role: string;
  status: 'active' | 'inactive' | 'banned';
  avatar: string;
  verified: boolean;
}

// Sample data
const userData: User[] = [
  {
    id: '1',
    name: 'Adam Trantow',
    email: 'adam@example.com',
    company: 'Mohr, Langworth and Hills',
    role: 'UI Designer',
    status: 'active',
    avatar: '/avatars/avatar-1.jpg',
    verified: true
  },
  {
    id: '2',
    name: 'Angel Rolfson-Kulas',
    email: 'angel@example.com',
    company: 'Koch and Sons',
    role: 'UI Designer',
    status: 'active',
    avatar: '/avatars/avatar-2.jpg',
    verified: true
  },
  {
    id: '3',
    name: 'Betty Hammes',
    email: 'betty@example.com',
    company: 'Waelchi - VonRueden',
    role: 'UI Designer',
    status: 'active',
    avatar: '/avatars/avatar-3.jpg',
    verified: true
  },
  {
    id: '4',
    name: 'Billy Braun',
    email: 'billy.b@example.com',
    company: 'White, Cassin and Goldner',
    role: 'UI Designer',
    status: 'banned',
    avatar: '/avatars/avatar-4.jpg',
    verified: false
  },
  {
    id: '5',
    name: 'Billy Stoltenberg',
    email: 'billy.s@example.com',
    company: 'Medhurst, Moore and Franey',
    role: 'Leader',
    status: 'banned',
    avatar: '/avatars/avatar-5.jpg',
    verified: true
  },
];

function CustomerPage() {
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredUsers, setFilteredUsers] = useState<User[]>(userData);

  // Search handler
  const handleSearch = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setSearchTerm(value);
    
    if (value) {
      const filtered = userData.filter(user => 
        user.name.toLowerCase().includes(value.toLowerCase()) || 
        user.email.toLowerCase().includes(value.toLowerCase()) ||
        user.company.toLowerCase().includes(value.toLowerCase())
      );
      setFilteredUsers(filtered);
    } else {
      setFilteredUsers(userData);
    }
  };

  // Table column helper
  const columnHelper = createColumnHelper<User>();

  // Define columns for the table
  const columns = [
    columnHelper.accessor('name', {
      header: 'Tên',
      cell: info => (
        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
          <Avatar src={info.row.original.avatar} alt={info.getValue()} />
          <Box>
            <Typography variant="body1" fontWeight={500}>
              {info.getValue()}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {info.row.original.email}
            </Typography>
          </Box>
        </Box>
      )
    }),
    columnHelper.accessor('company', {
      header: 'Công ty',
    }),
    columnHelper.accessor('role', {
      header: 'Vai trò',
      cell: info => (
        <Typography variant="body2">
          {info.getValue()}
        </Typography>
      )
    }),
    columnHelper.accessor('verified', {
      header: 'Xác thực',
      cell: info => (
        info.getValue() ? (
          <CheckCircleIcon color="success" fontSize="small" />
        ) : (
          <Typography variant="body2" color="text.secondary">-</Typography>
        )
      )
    }),
    columnHelper.accessor('status', {
      header: 'Trạng thái',
      cell: info => {
        const status = info.getValue();
        let color = 'default';
        let bgColor = 'grey.100';
        
        if (status === 'active') {
          color = 'success';
          bgColor = 'success.lighter';
        } else if (status === 'banned') {
          color = 'error';
          bgColor = 'error.lighter';
        }
        
        return (
          <Chip 
            label={status === 'active' ? 'Hoạt động' : 'Đã khóa'} 
            size="small"
            color={color as any}
            sx={{ 
              fontWeight: 500,
              bgcolor: bgColor,
              borderRadius: '16px'
            }}
          />
        );
      }
    }),
    columnHelper.accessor('id', {
      header: 'Thao tác',
      cell: info => (
        <Box sx={{ display: 'flex', gap: 1 }}>
          <IconButton size="small" color="primary">
            <EditIcon fontSize="small" />
          </IconButton>
          {info.row.original.status === 'active' ? (
            <IconButton size="small" color="error">
              <BlockIcon fontSize="small" />
            </IconButton>
          ) : (
            <IconButton size="small" color="success">
              <CheckCircleIcon fontSize="small" />
            </IconButton>
          )}
          <IconButton size="small" color="error">
            <DeleteIcon fontSize="small" />
          </IconButton>
        </Box>
      )
    })
  ];

  // Handle user actions
  const handleAddUser = () => {
    console.log('Add user clicked');
  };

  const handleEditUser = (userId: string) => {
    console.log('Edit user', userId);
  };

  const handleDeleteUser = (userId: string) => {
    console.log('Delete user', userId);
  };

  return (
    <Container maxWidth={false} sx={{ py: 3 }}>
      <Box sx={{ mb: 4, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant="h4" fontWeight={600}>
          Người dùng
        </Typography>
        <Button 
          variant="outlined"
          color="primary"
          startIcon={<AddIcon />}
          onClick={handleAddUser}
          sx={{ 
            borderRadius: '8px', 
            boxShadow: 'none',
            textTransform: 'none'
          }}
        >
          Thêm người dùng
        </Button>
      </Box>
      
      <Box sx={{ mb: 3 }}>
        <TextField
          placeholder="Tìm kiếm người dùng..."
          variant="outlined"
          fullWidth
          value={searchTerm}
          onChange={handleSearch}
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon color="action" />
              </InputAdornment>
            ),
            sx: { 
              borderRadius: '8px',
              bgcolor: 'background.paper'
            }
          }}
        />
      </Box>
      
      <CustomTable 
        columns={columns} 
        data={filteredUsers} 
      />
    </Container>
  );
}

export default CustomerPage;