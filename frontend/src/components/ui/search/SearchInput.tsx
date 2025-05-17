"use client";
import { TextField, InputAdornment, CircularProgress } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useCallback, useState, useEffect } from "react";
import { debounce } from "@/utils/debounce";

interface SearchInputProps {
  onSearch: (value: string) => void;
  placeholder?: string;
  debounceTime?: number;
  className?: string;
  isLoading?: boolean;
}

export function SearchInput({
  onSearch,
  placeholder = "Search...",
  debounceTime = 500,
  className,
  isLoading = false,
}: SearchInputProps) {
  const [value, setValue] = useState("");

  // Create debounced search function
  const debouncedSearch = useCallback(
    debounce((searchTerm: string) => {
      onSearch(searchTerm);
    }, debounceTime),
    [onSearch, debounceTime]
  );

  // Cleanup on unmount
  useEffect(() => {
    return () => {
      debouncedSearch.cancel();
    };
  }, [debouncedSearch]);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newValue = event.target.value;
    setValue(newValue);
    debouncedSearch(newValue);
  };

  return (
    <TextField
      value={value}
      onChange={handleChange}
      placeholder={placeholder}
      className={className}
      variant="outlined"
      size="small"
      fullWidth
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            {isLoading ? (
              <CircularProgress size={20} />
            ) : (
              <SearchIcon />
            )}
          </InputAdornment>
        ),
        className: "bg-white rounded-lg",
      }}
    />
  );
} 