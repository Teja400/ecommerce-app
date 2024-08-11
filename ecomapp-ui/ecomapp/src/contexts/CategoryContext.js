import axios from 'axios';
import React, { createContext, useState, useEffect, useContext } from 'react';

export const CategoryContext = createContext();

export const CategoryProvider = props => {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get('http://localhost:8111/ecomapp/api/categories', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
        }})
            .then((res) => {
                const categoriesData = res.data;
                if (categoriesData.length === 0) {
                    console.log('No categories found')
                }
                else {
                    console.log(res.data);
                    setCategories(res.data);
                    setLoading(false);
                }

            })
            .catch((err) => {
                console.log(err);
            })
            .finally(() => {
                console.log('It is over');
            })
    }, []);

    return (
        <CategoryContext.Provider value={[categories, loading, setCategories]}>
            {props.children}
        </CategoryContext.Provider>
    );
};

export const useCategories = () => useContext(CategoryContext);