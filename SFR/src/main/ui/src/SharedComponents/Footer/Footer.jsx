import {Navbar, Container} from "react-bootstrap";
import './Footer.css';
export default function Footer() {
    return (
        <div>
            <Navbar className="Footer">
                    <Container >
                        <p className = "text_center text-light">
                            ©{new Date().getFullYear()}. SFR. Municipalidad de San Pablo de Heredia. 
                        </p>
                    </Container>
            </Navbar>
        </div>
    );
}