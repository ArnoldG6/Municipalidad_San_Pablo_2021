import {Navbar, Container} from "react-bootstrap";
import './Footer.css';
export default function Footer() {
    return (
        <Navbar className="Footer" fixed="bottom">
                <Container >
                    <p className = "text_center text-light">
                        ©{new Date().getFullYear()}. Municipalidad de San Pablo de Heredia. 
                    </p>
                </Container>
        </Navbar>
    );
}