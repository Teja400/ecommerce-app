import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { ProtectedRoute } from "../components/ProtectRoute/ProtectedRoute";
import ContactUsPage from "../pages/ContactUsPage/ContactUsPage";
import AboutPage from "../pages/AboutPage/AboutPage";
import FaqPage from "../pages/FaqPage/FaqPage";
import ProductsPage from "../pages/ProductsPage/ProductsPage";
import CategoriesPage from "../pages/CategoriesPage/CategoriesPage";
import HomePage from "../pages/HomePage/HomePage";
import LoginPage from "../pages/LoginPage/LoginPage";
import RegisterPage from "../pages/RegisterPage/RegisterPage";

const Routes = () => {
  const { token } = useAuth();

  // Define public routes accessible to all users
  const routesForPublic = [
    {
      path: "/contact",
      element: <ContactUsPage />,
    },
    {
      path: "/about",
      element: <AboutPage />,
    },
    {
        path: "/about",
        element: <AboutPage />,
    },
    {
        path: "/faq",
        element: <FaqPage />,
    }
  ];

  // Define routes accessible only to authenticated users
  const routesForAuthenticatedOnly = [
    {
      path: "/",
      element: <ProtectedRoute />, // Wrap the component in ProtectedRoute
      children: [
        {
          path: "/products",
          element: <ProductsPage />,
        },
        {
          path: "/categories",
          element: <CategoriesPage />,
        }
      ],
    },
  ];

  // Define routes accessible only to non-authenticated users
  const routesForNotAuthenticatedOnly = [
    {
      path: "/",
      element: <HomePage/>,
    },
    {
      path: "/login",
      element: <LoginPage />,
    },
    {
      path: "/register",
      element: <RegisterPage />,
    },
  ];

  // Combine and conditionally include routes based on authentication status
  const router = createBrowserRouter([
    ...routesForPublic,
    ...(!token ? routesForNotAuthenticatedOnly : []),
    ...routesForAuthenticatedOnly,
  ]);

  // Provide the router configuration using RouterProvider
  return <RouterProvider router={router} />;
};

export default Routes;