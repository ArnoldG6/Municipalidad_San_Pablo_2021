import { Navbar, Container, Image, Stack } from "react-bootstrap";
import './Footer.css';
import logoSFR from "../images/logoSFR.png"

export default function Footer() {
    return (
        <div>
            <Navbar className="Footer">
                <Container fluid>
                    <Stack direction="vertical" gap={1}>
                        <Image src={logoSFR} fluid height={25} width={100} className="image_center"/>
                        <p className="align-middle text_center">
                            ©{new Date().getFullYear()}. Municipalidad de San Pablo de Heredia.
                        </p>
                    </Stack>
                </Container>
            </Navbar>
        </div>
    );
}