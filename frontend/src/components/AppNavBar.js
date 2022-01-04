import { Navbar, NavbarBrand } from 'reactstrap';

const NavBar = () => {
    return <Navbar color="dark" dark expand="md" fixed="top">
        <NavbarBrand className="m-auto">Училище</NavbarBrand>
        <NavbarBrand>Име на ученик</NavbarBrand>
    </Navbar>;
}

export default NavBar