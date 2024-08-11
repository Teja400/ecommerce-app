import axios from "axios";
const handleDelete = (id, products, setProducts) => {
    axios.delete('http://localhost:8111/ecomapp/api/products/' + id, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    
    })
    .then((res) => {
      console.log(res);
      setProducts(products.filter((product) => product.id !== id));
    })
    .catch((err) => {
      console.log(err);
    })
    .finally(() => {
      console.log('It is over');
    })
  }
export default handleDelete;