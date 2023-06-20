export const CardCapes = ({ title, value, icon, color }) => {
  return (
    <div className="col-md-3 col-sm-6 col-12">
      <div className="info-box">
        <span className={`info-box-icon bg-${color}`}>
          <i className={`far fa-${icon}`}></i>
        </span>

        <div className="info-box-content">
          <span className="info-box-text">{title}</span>
          <span className="info-box-number">{value}</span>
        </div>
      </div>
    </div>
  );
};
