import axios from "axios";

const handleDelete = (id, categories, setCategories) => {
    axios.delete('http://localhost:8111/ecomapp/api/categories/' + id, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    
    })
    .then((res) => {
      console.log(res);
      setCategories(categories.filter((category) => category.id !== id));
    })
    .catch((err) => {
      console.log(err);
    })
    .finally(() => {
      console.log('It is over');
    })
  }
export default handleDelete;