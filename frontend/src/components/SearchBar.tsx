import React, { useState, useEffect, useCallback } from 'react';
import { SearchBarProps } from '../interfaces'

const SearchBar: React.FC<SearchBarProps> = ({ onSearch, placeholderText }) => {
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [debouncedSearchTerm, setDebouncedSearchTerm] = useState<string>('');
  
  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedSearchTerm(searchTerm);
    }, 500);

    return () => {
      clearTimeout(handler);
    };
  }, [searchTerm]);

  const fetchResults = useCallback(async (query: string) => {
    onSearch(query);
  }, []);

  useEffect(() => {
    fetchResults(debouncedSearchTerm);
  }, [debouncedSearchTerm, fetchResults]);

  return (
    <div className="searchbar">
      <input
        type="text"
        placeholder={placeholderText}
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
    </div>
  );
};

export default SearchBar;
