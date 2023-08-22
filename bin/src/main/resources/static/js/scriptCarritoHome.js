document.addEventListener("DOMContentLoaded", function() {
    const aviso = document.querySelector(".aviso");
    const cerrarAviso = document.getElementById("cerrarAviso");

    if (aviso) {
        aviso.style.display = "block";

        if (cerrarAviso) {
            cerrarAviso.addEventListener("click", function() {
                aviso.style.display = "none";
            });
        }
    }
});
 