export default function Header({titulo}) {
    return (
        <div className="content-header">
            <div className="container">
                <div className="row mb-2">
                <div className="col-sm-6">
                    <h1 className="m-0"> {titulo} </h1>
                </div>
                </div>
            </div>
        </div>
    );
}